/*
 * TaskLink.java
 *
 * Copyright (C) 2008  Pei Wang
 *
 * This file is part of Open-NARS.
 *
 * Open-NARS is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Open-NARS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open-NARS.  If not, see <http://www.gnu.org/licenses/>.
 */
package nars.entity;

import nars.core.Parameters;
import nars.language.Term;

/**
 * Reference to a Task.
 * <p>
 * The reason to separate a Task and a TaskLink is that the same Task can be
 * linked from multiple Concepts, with different BudgetValue.
 * 
 * TaskLinks are unique according to the Task they reference
 */
public class TaskLink extends Item<Task> implements TLink {

    /**
     * The Task linked. The "target" field in TermLink is not used here.
     */
    public final Task targetTask;
    
    /**
     * Remember the TermLinks that has been used recently with this TaskLink
     */
    public final TermLink recordedLinks[];
    
    /**
     * Remember the time when each TermLink is used with this TaskLink
     */
    public final long recordingTime[];
    
    /**
     * The number of TermLinks remembered
     */
    private int counter;
    
    /** The type of link, one of the above */    
    public final short type;

    /** The index of the component in the component list of the compound, may have up to 4 levels */
    public final short[] index;

    
    /**
     * Constructor
     * <p>
     * only called in Memory.continuedProcess
     *
     * @param t The target Task
     * @param template The TermLink template
     * @param v The budget
     */
    public TaskLink(final Task t, final TermLink template, final BudgetValue v, int recordLength) {
        super(v);
        this.type =
                template == null ? 
                        TermLink.SELF : 
                        template.type;
        this.index =
                
                template == null ?
                        null : 
                        template.index
        ;
        
        targetTask = t;
        recordedLinks = new TermLink[recordLength];
        recordingTime = new long[recordLength];
        counter = 0;        
    }


    @Override
    public int hashCode() {        
        return targetTask.hashCode();                
    }

    @Override
    public Task name() {
        return targetTask;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof TaskLink) {
            TaskLink t = (TaskLink)obj;
            return t.targetTask.equals(targetTask);                    
        }
        return false;
    }    
    
    /**
     * Get one index by level
     * @param i The index level
     * @return The index value
     */
    @Override
    public final short getIndex(final int i) {
        if ((index != null) && (i < index.length)) {
            return index[i];
        } else {
            return -1;
        }
    }    
    
    
    
    /**
     * Get the target Task
     *
     * @return The linked Task
     */
    public Task getTargetTask() {
        return targetTask;
    }

    /**
     * To check whether a TaskLink should use a TermLink, return false if they
     * interacted recently
     * <p>
     * called in TermLinkBag only
     *
     * @param termLink The TermLink to be checked
     * @param currentTime The current time
     * @return Whether they are novel to each other
     */
    public boolean novel(final TermLink termLink, final long currentTime) {
        final Term bTerm = termLink.target;
        if (bTerm.equals(targetTask.sentence.content)) {            
            return false;
        }
        TermLink linkKey = termLink.name();
        int next, i;
        
        final int recordLength = recordedLinks.length; //Parameters.TERM_LINK_RECORD_LENGTH;
        
        for (i = 0; i < counter; i++) {
            next = i % recordLength;
            if (linkKey.equals(recordedLinks[next])) {
                if (currentTime < recordingTime[next] + recordLength) {
                    return false;
                } else {
                    recordingTime[next] = currentTime;
                    return true;
                }
            }
        }
        next = i % recordLength;
        recordedLinks[next] = linkKey;       // add knowledge reference to recordedLinks
        recordingTime[next] = currentTime;
        if (counter < recordLength) { // keep a constant length
            counter++;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " " + getTargetTask().sentence.stamp;
    }
    
    public String toStringBrief() {
        return super.toString();
    }

    @Deprecated public void updateTaskPriority() {
        targetTask.budget.lerpPriority(budget.getPriority(), Parameters.TASK_PRIORITY_MOMENTUM);
    }
}

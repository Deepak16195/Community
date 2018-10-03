/*
 * Copyright 2016 - 2017 ShineM (Xinyuan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under.
 */

package com.rdavepatient.soft.Community.TreeViewPage;

import android.view.View;

/**
 * Created by Dignity on 19-11-2017.
 */


public abstract class CheckableNodeViewBinder extends BaseNodeViewBinder {

    public CheckableNodeViewBinder(View itemView) {
        super(itemView);
    }

    /**
     * Get the checkable view id. MUST BE A CheckBox CLASS！
     *
     * @return
     */
    public abstract int getCheckableViewId();

    /**
     * Do something when a node select or deselect（only triggered by clicked）
     *
     * @param treeNode
     * @param selected
     */
    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}

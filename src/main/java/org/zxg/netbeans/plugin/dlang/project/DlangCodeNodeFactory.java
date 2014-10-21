/*
 * Copyright (C) 2014 Xianguang Zhou <xianguang.zhou@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.zxg.netbeans.plugin.dlang.project;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 */
@NodeFactory.Registration(projectType = "org-dlang-project", position = 10)
public class DlangCodeNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        DlangProject p = project.getLookup().lookup(DlangProject.class);
        assert p != null;
        return new DlangCodeNodeList(p);
    }

    private class DlangCodeNodeList implements NodeList<Node> {

        DlangProject project;

        public DlangCodeNodeList(DlangProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {
            FileObject srcFolder
                    = project.getProjectDirectory().getFileObject("src");
            List<Node> result = new ArrayList<Node>();
            if (srcFolder != null) {
                try {
                    result.add(DataObject.find(srcFolder).getNodeDelegate());
//                    for (FileObject textsFolderFile : srcFolder.getChildren()) {
//                        try {
//                            result.add(DataObject.find(textsFolderFile).getNodeDelegate());
//                        } catch (DataObjectNotFoundException ex) {
//                            Exceptions.printStackTrace(ex);
//                        }
//                    }
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return result;
        }

        @Override
        public Node node(Node node) {
            return new FilterNode(node);
        }

        @Override
        public void addNotify() {
        }

        @Override
        public void removeNotify() {
        }

        @Override
        public void addChangeListener(ChangeListener cl) {
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
        }

    }
}

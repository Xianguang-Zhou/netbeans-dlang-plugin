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

import java.io.IOException;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 */
@ServiceProvider(service = ProjectFactory.class)
public class DlangProjectFactory implements ProjectFactory2 {

    public static final String PROJECT_FILE = "netbeans-dlang-project.xml";

    @Override
    public boolean isProject(FileObject fo) {
        return fo.getFileObject(PROJECT_FILE) != null;
    }

    @Override
    public Project loadProject(FileObject fo, ProjectState ps) throws IOException {
        if (isProject(fo)) {
            DlangProject project = new DlangProject(fo, ps);
            return project;
        } else {
            return null;
        }
    }

    @Override
    public void saveProject(Project prjct) throws IOException, ClassCastException {
    }

    @Override
    public ProjectManager.Result isProject2(FileObject fo) {
        if (isProject(fo)) {
            return new ProjectManager.Result(new ImageIcon(ImageUtilities.loadImage(DlangProject.Info.DLANG_ICON)));
        } else {
            return null;
        }
    }

}

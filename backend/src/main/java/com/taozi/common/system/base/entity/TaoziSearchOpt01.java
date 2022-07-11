package com.taozi.common.system.base.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaoziSearchOpt01 {
    protected List<String> project_id;
    protected List<String> project_name;
    protected List<String> road_code;
    protected List<String> maintain;
    protected List<String> line_dir;
    protected List<String> custom01;
    protected List<String> custom02;

    public TaoziSearchOpt01() {
        project_id = new ArrayList<>();
        project_name = new ArrayList<>();
        road_code = new ArrayList<>();
        maintain = new ArrayList<>();
        line_dir = new ArrayList<>();
        line_dir.add("全幅");
        line_dir.add("上行");
        line_dir.add("下行");
        custom01 = new ArrayList<>();
        custom02 = new ArrayList<>();
    }

    public void addProjectId(String projectId) {
        if (!this.project_id.contains(projectId)) {
            this.project_id.add(projectId);
        }
    }

    public void addProjectName(String projectName) {
        if (!this.project_name.contains(projectName)) {
            this.project_name.add(projectName);
        }
    }

    public void addRoadCode(String roadCode) {
        if (!this.road_code.contains(roadCode)) {
            this.road_code.add(roadCode);
        }
    }

    public void addMaintain(String maintain) {
        if (!this.maintain.contains(maintain)) {
            this.maintain.add(maintain);
        }
    }

    public void addLineDir(String lineDir) {
        if (!this.line_dir.contains(lineDir)) {
            this.line_dir.add(lineDir);
        }
    }

    public void addCustom01(String custom01) {
        if (!this.custom01.contains(custom01)) {
            this.custom01.add(custom01);
        }
    }

    public void addCustom02(String custom02) {
        if (!this.custom02.contains(custom02)) {
            this.custom02.add(custom02);
        }
    }
}

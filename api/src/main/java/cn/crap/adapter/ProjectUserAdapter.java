package cn.crap.adapter;

import cn.crap.dto.PermissionDTO;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.model.Project;
import cn.crap.model.ProjectUserPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.PermissionUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ProjectUserAdapter {
    public static ProjectUserDto getDto(ProjectUserPO model, Project project){
        if (model == null){
            return null;
        }

        ProjectUserDto dto = new ProjectUserDto();
		BeanUtil.copyProperties(model, dto);
		dto.setProjectName(project == null ? "" : project.getName());
		dto.setCrShowPermissionSet(PermissionUtil.getSet(model.getPermission()));
		List<PermissionDTO> permissionLit = Lists.newArrayList();
		StringBuilder permissionSb = new StringBuilder();
        dto.getCrShowPermissionSet().stream().forEach(value -> {
            ProjectPermissionEnum permissionEnum = ProjectPermissionEnum.getByValue(value);
            if (permissionEnum != null){
                permissionLit.add(new PermissionDTO(permissionEnum.getValue(), permissionEnum.getDesc()));
                permissionSb.append(permissionEnum.getDesc() + ",");
            }
        });
        dto.setCrShowPermissionList(permissionLit);
        dto.setPermissionStr(permissionSb.toString());
        dto.setSequence(System.currentTimeMillis());

        return dto;
    }

    public static ProjectUserPO getModel(ProjectUserDto dto){
        if (dto == null){
            return null;
        }
        ProjectUserPO model = new ProjectUserPO();
		BeanUtil.copyProperties(dto, model);
        return model;
    }

    public static List<ProjectUserDto> getDto(List<ProjectUserPO> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectUserDto> dtos = new ArrayList<>();
        for (ProjectUserPO model : models){
            dtos.add(getDto(model, null));
        }
        return dtos;
    }
}

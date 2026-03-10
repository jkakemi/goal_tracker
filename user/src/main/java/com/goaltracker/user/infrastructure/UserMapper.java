package com.goaltracker.user.infrastructure;

import com.goaltracker.user.domain.SkillsEnum;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.infrastructure.persistence.UserEntity;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getPublicId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getXpTotal(),
                user.getLevel(),
                user.getSkills().toString(),
                user.userIsActive(),
                user.getCreated_at(),
                user.getUpdated_at(),
                user.getUpdated_at(),
                user.getRole()
        );
    }

    public User toDomain(UserEntity entity) {
        Set<SkillsEnum> skillsUser = new HashSet<>();
        Set<SkillsEnum> skillsTotal = generateSkillsListComplet();
        skillsTotal.forEach(s -> {
            if(entity.getSkills().contains(s.name())){
            skillsUser.add(s);
        }});

        return new User(
                entity.getPublicId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getXpTotal(),
                entity.getLevel(),
                skillsUser,
                entity.isActive(),
                entity.getCreated_at(),
                entity.getUpdated_at(),
                entity.getUpdated_at(),
                entity.getRole()
        );
    }

    public void updateEntity(UserEntity entity, User user) {
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setXpTotal(user.getXpTotal());
        entity.setDeleted_at(user.getDeleted_at());
        entity.setUpdated_at(user.getUpdated_at());
        entity.setSkills(user.getSkills().toString());
        entity.setActive(user.userIsActive());
    }

    public Set<SkillsEnum> generateSkillsListComplet(){
        Set<SkillsEnum> result = new HashSet<>();
        result.add(SkillsEnum.FINANCEIRO);
        result.add(SkillsEnum.ESTUDOS);
        result.add(SkillsEnum.SAUDE);
        result.add(SkillsEnum.PRODUTIVIDADE);
        result.add(SkillsEnum.CRESCIMENTO_PESSOAL);
        result.add(SkillsEnum.BEM_ESTAR);
        result.add(SkillsEnum.TRABALHO);
        result.add(SkillsEnum.OUTROS);

        return result;
    }
}

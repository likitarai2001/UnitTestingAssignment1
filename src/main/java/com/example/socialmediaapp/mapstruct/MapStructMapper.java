package com.example.socialmediaapp.mapstruct;

import com.example.socialmediaapp.dto.UserProfileDto;
import com.example.socialmediaapp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    @Mappings(
            value = {
                @Mapping(target = "bio", source = "profile.bio"),
                @Mapping(target = "dob", source = "profile.dob"),
                @Mapping(target = "gender", source = "profile.gender")
            })
    UserProfileDto userToUserProfileDto(User user);
}

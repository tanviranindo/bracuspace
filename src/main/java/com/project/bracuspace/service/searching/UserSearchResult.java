package com.project.bracuspace.service.searching;

import com.project.bracuspace.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResult {
    private Page<UserDTO> userPage;
    private boolean numberFormatException;
}

package com.artjomkuznetsov.healthhub.security.customUserDetails;

import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public CustomUserDetailsImpl(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElse(null);

        if (user == null) {
            Doctor doctor = doctorRepository.findByEmail(username)
                    .orElse(null);
            if (doctor != null) {
                return new CustomUserDetails(
                        doctor.getId(),
                        doctor.getUsername(),
                        doctor.getPassword(),
                        doctor.getAuthorities()
                );
            }
        } else {
            return new CustomUserDetails(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        }
        throw new UsernameNotFoundException(username);
    }
}

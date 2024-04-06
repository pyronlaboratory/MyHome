package com.myhome.security;

import com.myhome.domain.User;
import com.myhome.services.CommunityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 */
public class CommunityAuthorizationFilter extends BasicAuthenticationFilter {
    private final CommunityService communityService;
    private final String uuidPattern = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    private final Pattern addAdminRequestPattern = Pattern.compile("/communities/" + uuidPattern + "/admins");


    public CommunityAuthorizationFilter(AuthenticationManager authenticationManager,
                                        CommunityService communityService) {
        super(authenticationManager);
        this.communityService = communityService;
    }

    /**
     * filters incoming HTTP requests based on a pattern and checks if the user is an
     * admin or not before passing the request to the next filter in the chain.
     * 
     * @param request HTTP request received by the filter.
     * 
     * 	- `getRequestURI()`: Returns the String representation of the request URI.
     * 	- `isUserCommunityAdmin()`: A boolean method that checks if the user is an
     * administrator for a community.
     * 
     * The function then performs its internal processing and delegates to the superclass's
     * `doFilterInternal` method, passing in the original input `request`.
     * 
     * @param response HttpServletResponse object that is used to handle the filtered request.
     * 
     * 	- `HttpServletResponse`: This is an instance of the `HttpServletResponse` class,
     * which represents the response object in an HTTP request-response cycle. It contains
     * various attributes and methods related to handling HTTP requests and responses.
     * 	- `status`: The `status` attribute of the `HttpServletResponse` object indicates
     * the status code returned by the server. In this function, it is set to `SC_UNAUTHORIZED`.
     * 	- ` ServletException`: This is an exception that is thrown if the filter chain
     * cannot handle the request due to a security-related reason. It is caught and handled
     * in the `doFilterInternal` function.
     * 
     * @param chain Chain of filters to execute.
     * 
     * 	- `request`: The original HTTP request that was passed to the filter chain.
     * 	- `response`: The HTTP response object that is being filtered.
     * 	- `chain`: An instance of `FilterChain`, which represents the filter chain in
     * which the current filter is nested.
     * 	- `super`: A reference to the parent `doFilterInternal` function, which will be
     * called if the current filter does not handle the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        Matcher urlMatcher = addAdminRequestPattern.matcher(request.getRequestURI());

        if (urlMatcher.find() && !isUserCommunityAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        super.doFilterInternal(request, response, chain);
    }

    /**
     * determines if a user is an administrator of a community based on their user ID and
     * the community ID in the request URL. If the user ID matches the ID of an admin for
     * the community, the function returns `true`. Otherwise, it returns `false`.
     * 
     * @param request HTTP request that triggered the method, providing the community ID
     * from the request URI.
     * 
     * 1/ `getRequestURI()`: This method returns the string representation of the request
     * URI, which is the part of the URL after the domain name.
     * 2/ `split("/")`: This method splits the request URI into an array of strings using
     * the "/" character as the delimiter. The second element in the array is the community
     * ID.
     * 3/ `getAuthentication()`: This method returns the current authentication context,
     * which contains information about the user who made the request.
     * 4/ `getPrincipal()`: This method returns the principal (i.e., user) associated
     * with the current authentication context.
     * 5/ `findCommunityAdminsById(communityId, null)`: This method finds a list of
     * community admins for a given community ID using the `communityService` interface.
     * The second argument `null` means that no filter is applied to the list of admins.
     * 6/ `stream()`: This method creates a stream from the list of community admins.
     * 7/ `filter(communityAdmin -> communityAdmin.getUserId().equals(userId))` : This
     * method filters the stream of community admins to find the user ID that matches the
     * input `userId`. The `filter()` method takes a lambda expression as an argument,
     * which in this case checks if the `getUserId()` method returns the same value as
     * the input `userId`.
     * 8/ `findFirst()`: This method finds the first element in the filtered stream of
     * community admins that matches the input `userId`. If no match is found, it returns
     * `null`.
     * 9/ `orElse(null)`: This method returns the result of the previous call to `findFirst()`
     * or `null` if no match was found.
     * 
     * In summary, the `request` object contains information about the user who made the
     * request and the community ID that is being checked for admin privileges.
     * 
     * @returns a boolean value indicating whether the current user is an admin of the
     * specified community.
     */
    private boolean isUserCommunityAdmin(HttpServletRequest request) {
        String userId = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String communityId = request
                .getRequestURI().split("/")[2];
        Optional<List<User>> optional = communityService
                .findCommunityAdminsById(communityId, null);

        if (optional.isPresent()) {
            List<User> communityAdmins = optional.get();
            User admin = communityAdmins
                    .stream()
                    .filter(communityAdmin -> communityAdmin.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);

            return admin != null;
        }

        return false;
    }
}
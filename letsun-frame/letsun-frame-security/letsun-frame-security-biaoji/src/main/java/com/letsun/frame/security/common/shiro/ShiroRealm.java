package com.letsun.frame.security.common.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letsun.frame.security.system.domain.Menu;
import com.letsun.frame.security.system.domain.Role;
import com.letsun.frame.security.system.domain.User;
import com.letsun.frame.security.system.domain.UserLogin;
import com.letsun.frame.security.system.service.UserLoginService;

/**
 * @Desc 登录验证与授权
 * @author YY<2017年10月17日>
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
	
	@SuppressWarnings("serial")
	public static final List<String> AUTHORIZ_SOVEREIGN_ACCOUNT = new ArrayList<String>(){{add("manager");}}; 
	
	@Autowired
	private UserLoginService userLoginService;
	
	/**
	 * @Desc 登录认证 
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 * @author YY<2017年10月18日>
	 */
	@Override 
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
        String account = token.getUsername();  
        if(StringUtils.isNotEmpty(account)){
        	UserLogin userLogin = userLoginService.findUserByAccount(account);
        	if (userLogin == null || userLogin.getUser() == null) {
        		return null;
    		}
        	//状态:0启用1锁定/冻结
        	if(userLogin.getUser().getStatus() != 0){
        		throw new LockedAccountException("对不起，您的账号已被冻结");
        	}
        
        	if(userLogin.getUser().getDeleted() != 0){
        		throw new LockedAccountException("对不起，您的账号已被删除");
        	}
        	
        	SecurityUtils.getSubject().getSession().setAttribute("currentUserLogin", userLogin);
            return new SimpleAuthenticationInfo(userLogin.getAccount(),userLogin.getPassword(),getName());
        }  
        return null;  
	}

	/**
	 * @Desc 用户授权 
	 * @param principals
	 * @return
	 * @author YY<2017年10月18日>
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {		

		UserLogin userLogin = (UserLogin) SecurityUtils.getSubject().getSession().getAttribute("currentUserLogin");
		//角色列表
		List<String> roles = new ArrayList<String>();
		//功能列表
		List<String> menuFunctionCodes = new ArrayList<String>();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("default");
		
		// 设置权限串
		if(AUTHORIZ_SOVEREIGN_ACCOUNT.contains(userLogin.getAccount())){
			info.addStringPermission("*:*");
		}else{		
			User user =userLogin.getUser();
			if(user==null || user.getRoles()==null || user.getRoles().isEmpty()){
				return null;	
			}
	    	for(Role role : user.getRoles()){
	    		roles.add(role.getName());
	    		List<Menu> menus = role.getMenus();
	    		if(menus == null || menus.isEmpty()){
	    			continue;
	    		}
	    		for(Menu menu : menus){
	    			String menuCode = menu.getUrl();
	    			if(menuCode == null || StringUtils.isBlank(menuCode)){
	    				continue;
	    			}
	    			String[] menuCodes = menuCode.split(",");
    				for(String subMenuCode:menuCodes){
    					if(StringUtils.isNotBlank(subMenuCode) && !menuFunctionCodes.contains(subMenuCode)){
    	    				menuFunctionCodes.add(subMenuCode);
    	    			}
    				}
	    		}
	    	}
		}    	
		info.addRoles(roles);
		info.addStringPermissions(menuFunctionCodes);
		return info;
	}

}

package funfall.managers;

import funfall.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManagement {
    public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<UUID, PermissionAttachment>();
    Main main;
    public void setupPermissions(Player player){
        PermissionAttachment attachment = player.addAttachment(main.plugin);
        this.playerPermissions.put(player.getUniqueId(), attachment);
        permissionSetter(player.getUniqueId());
    }

    public void setupGroupPermissions(String group, String permission){
        List<String> permissionList = main.fm.getGroups().getStringList("groups." + group + ".permissions");
        permissionList.add(permission);
        main.fm.getGroups().set("groups." + group + ".permissions", permission);
        main.fm.saveGroups();
    }

    private void permissionSetter(UUID uuid){
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        for (String groups: main.fm.getGroups().getConfigurationSection("groups").getKeys(false)){
            for(String permissions: main.fm.getGroups().getStringList("groups." + groups + ".permissions")){
                attachment.setPermission(permissions, true);
            }
        }
    }

}

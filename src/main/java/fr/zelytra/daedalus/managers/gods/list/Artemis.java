package fr.zelytra.daedalus.managers.gods.list;

import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.gods.Gods;
import fr.zelytra.daedalus.managers.items.CustomItemStack;
import fr.zelytra.daedalus.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;

public class Artemis implements Gods {

    public Artemis(Faction team) {
        init(team);
    }

    public Artemis(){};

    @Override
    public double teamHeart() {
        return 20;
    }

    @Override
    public ArrayList<ItemStack> godItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = new ItemStack(Material.BOW);
        item.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        items.add(item);
        items.add(new CustomItemStack(CustomMaterial.ARTEMIS_HORN).getItem());
        return items;
    }

    @Override
    public ArrayList<ItemStack> teamItems() {
        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(Enchantment.ARROW_DAMAGE, 2, false);
        meta.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
        item.setItemMeta(meta);
        items.add(item);
        items.add(new ItemStack(Material.SPECTRAL_ARROW,64));
        return items;
    }

    @Override
    public Collection<PotionEffect> godEffects() {
        Collection<PotionEffect> potions = new ArrayList<>();
        potions.add(new PotionEffect(PotionEffectType.SPEED,99999999,0,false,false,true));
        return potions;
    }

    @Override
    public Collection<PotionEffect> teamEffects() {
        Collection<PotionEffect> potions = new ArrayList<>();
        potions.add(new PotionEffect(PotionEffectType.SPEED,99999999,0,false,false,true));
        return potions;
    }
}

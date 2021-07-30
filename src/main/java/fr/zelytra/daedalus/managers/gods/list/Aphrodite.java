package fr.zelytra.daedalus.managers.gods.list;

import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.gods.Gods;
import fr.zelytra.daedalus.managers.items.CustomItemStack;
import fr.zelytra.daedalus.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

public class Aphrodite implements Gods {

    public Aphrodite(Faction faction) {
        init(faction);
    }

    public Aphrodite() {

    }

    @Override
    public double teamHeart() {
        return 20;
    }

    @Override
    public ArrayList<ItemStack> godItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new CustomItemStack(CustomMaterial.APHRODITE_HEART).getItem());
        return items;
    }

    @Override
    public ArrayList<ItemStack> teamItems() {

        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        item.addEnchantment(Enchantment.DURABILITY, 2);
        items.add(item);
        return items;
    }

    @Override
    public Collection<PotionEffect> godEffects() {
        return null;
    }

    @Override
    public Collection<PotionEffect> teamEffects() {
        return null;
    }
}

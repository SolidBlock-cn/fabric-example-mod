package net.fabricmc.example;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

public class MyCommand implements Command<Object> {


    @Override
    public int run(CommandContext<Object> context) {
        return 0;
    }

}

package com.MuhammadNurmajiid.frontend.objects.item;
import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;

import com.MuhammadNurmajiid.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

    public class Item extends GameObject {
        private String itemType;
        private long scoreValue;
        private ItemType itemTypeEnum;
        private boolean collected;



        public Item(float x, float y, String itemType) {
            super(x, y, 16, 16, 100f, Color.WHITE);
            this.itemType = itemType;
            this.scoreValue = 1000L;
            this.collected = false;
        }

        public Item(float x, float y, float width, float height, float speed, String itemType) {
            super(x, y, width, height, speed, Color.WHITE);
            this.itemType = itemType;
            this.scoreValue = 1000L;
            this.collected = false;

        }

        public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue) {
            super(x, y, width, height, speed, Color.WHITE);
            this.itemType = itemType;
            this.scoreValue = scoreValue;
            this.collected = false;

        }

        public Item(float x, float y, ItemType itemTypeEnum) {
            super(x, y, 16, 16, 100f, Color.WHITE);
            this.itemTypeEnum = itemTypeEnum;
            this.itemType = itemTypeEnum.name();
            this.scoreValue = itemTypeEnum.getScoreValue();
            this.collected = false;

        }

        public Item(float x, float y, float width, float height, float speed, ItemType itemTypeEnum, long scoreValue) {
            super(x, y, width, height, speed, Color.WHITE);
            this.itemTypeEnum = itemTypeEnum;
            this.itemType = itemTypeEnum.name();
            this.scoreValue = scoreValue;
            this.collected = false;

        }

        public boolean isCollected() {
            return collected;
        }

        public void setCollected(boolean collected) {
            this.collected = collected;
        }

        public String getItemType() {
            return itemType;
        }

        public long getScoreValue() {
            return scoreValue;
        }

        public ItemType getItemTypeEnum() {
            return itemTypeEnum;
        }

        @Override
        public void update(float delta) {
            this.y -= speed * delta;
        }

        @Override
        public void onCollision(Collidable other) {
            if (other instanceof Player) {
                // Item pickup is handled on the Player side via collectItem()
            }
        }

    }

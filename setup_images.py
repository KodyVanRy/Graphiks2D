# !/usr/bin/env python

import os
import time
import pygame
import sys
from pygame.locals import *

static_out = list()
dynamic_out = list()

pygame.init()


def current_milli_time():
    return int(round(time.time() * 1000))


def get_assets_folder(directory):
    for root, dirs, files in os.walk(directory, topdown=False):
        if root.split("/").pop() == "assets":
            return root


def get_assets(directory):
    for root, dirs, files in os.walk(directory, topdown=False):
        if root.split("/").pop() == "assets":
            return files


def get_image_assets(files, root):
    return_files = list()
    for _file in files:
        print _file
        if _file.split(".")[0] + ".fnt" not in files \
                and ".prt" not in _file \
                and ".json" not in _file:
            return_files.append(os.path.join(root, _file))
    return return_files


def get_images(filenames):
    images = list()
    for filename in filenames:
        sm_filename = filename.split("/")
        print sm_filename
        images.append(Drawable(pygame.image.load(filename), sm_filename[len(sm_filename) - 1]))
    return images


class Drawable:
    def __init__(self, surface, filename):
        self.surface = surface
        self.second_surface = pygame.Surface((surface.get_width(), surface.get_height()), pygame.SRCALPHA)
        self.second_surface.blit(surface, (0, 0))
        self.filename = filename
        self.rect = None
        self.nine_patch = False
        self.nine_patch_left = surface.get_width() / 3
        self.nine_patch_right = surface.get_width() / 3
        self.nine_patch_top = surface.get_height() / 3
        self.nine_patch_bottom = surface.get_height() / 3
        self.dirty = False

    def get_static_name(self):
        return self.filename.upper().split(".")[0]

    def get_height(self):
        return self.surface.get_height()

    def get_width(self):
        return self.surface.get_width()

    def left(self, amount):
        print "left"
        self.nine_patch_left += amount
        self.dirty = True

    def right(self, amount):
        self.nine_patch_right += amount
        self.dirty = True

    def top(self, amount):
        self.nine_patch_top += amount
        self.dirty = True

    def bottom(self, amount):
        self.nine_patch_bottom += amount
        self.dirty = True

    def toggle_ninepatch(self):
        self.nine_patch = not self.nine_patch
        self.dirty = True

    def draw(self, surf, centered=True, pos=(0, 0)):
        if self.dirty:
            self.second_surface = pygame.Surface((self.get_width(), self.get_height()), pygame.SRCALPHA)
            self.second_surface.blit(self.surface, (0, 0))
            if self.nine_patch:
                pygame.draw.line(self.second_surface,
                                 (255, 0, 255),
                                 (self.nine_patch_left, 0),
                                 (self.nine_patch_left, self.get_height()))
                pygame.draw.line(self.second_surface,
                                 (255, 0, 255),
                                 (self.get_width() - self.nine_patch_right, 0),
                                 (self.get_width() - self.nine_patch_right, self.get_height()))
                pygame.draw.line(self.second_surface,
                                 (255, 0, 255),
                                 (0, self.nine_patch_top),
                                 (self.get_width(), self.nine_patch_top))
                pygame.draw.line(self.second_surface,
                                 (255, 0, 255),
                                 (0, self.get_height() - self.nine_patch_bottom),
                                 (self.get_width(), self.get_height() - self.nine_patch_bottom))
            self.dirty = False

        if centered:
            surf.blit(self.second_surface, (surf.get_width() / 2 - self.get_width() / 2,
                      surf.get_height() / 2 - self.get_height() / 2))
        else:
            surf.blit(self.second_surface, pos)


class BigPicture(pygame.Surface):
    def __init__(self, width, height):
        pygame.Surface.__init__(self, (width, height), pygame.SRCALPHA)

        self.surfaces = list()
        self.lasty = 0

    def add_image(self, _image, big_picture=0):
        print "add_image"
        for y in range(0, self.get_height()):
            for x in range(0, self.get_width()):
                draw = True
                for surface in self.surfaces:
                    if surface.rect.colliderect(
                            pygame.Rect(x, y, _image.get_width(), _image.get_height())):
                        draw = False
                if not self.get_rect().contains(
                        pygame.Rect(x, y, _image.get_width(), _image.get_height())):
                    draw = False
                if draw:
                    self.blit(_image.surface, (x, y))
                    _image.rect = pygame.Rect(x, y, _image.get_width(), _image.get_height())
                    self.surfaces.append(_image)
                    static_out.append(_image.get_static_name())
                    if _image.nine_patch:
                        dynamic_out.append("mAssetManager.addDrawable(" + _image.get_static_name() +
                                           ", new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(" +
                                           str(big_picture) + "), " +
                                           str(x) + ", " + str(y) + ", " +
                                           str(_image.get_width()) + ", " + str(_image.get_height()) + ")" +
                                           ", " + str(_image.nine_patch_left) + ", " + str(_image.nine_patch_right) +
                                           ", " + str(_image.nine_patch_top) + ", " + str(_image.nine_patch_bottom) +
                                           ")));")
                    else:
                        dynamic_out.append("mAssetManager.addDrawable(" + _image.get_static_name() +
                                           ", new Drawable(new TextureRegion(mAssetManager.getTexture(" +
                                           str(big_picture) + "), " +
                                           str(x) + ", " + str(y) + ", " +
                                           str(_image.get_width()) + ", " + str(_image.get_height()) + ")));")
                    return True

        return False

    def add_images(self, _images):

        image_num = 0
        _image = _images[image_num]
        while True:
            for y in range(0, self.get_height()):
                for x in range(0, self.get_width()):
                    draw = True
                    for surface in self.surfaces:
                        if surface.colliderect(pygame.Rect(x, y, _image.get_width(), _image.get_height())):
                            draw = False
                    if not self.get_rect().contains(pygame.Rect(x, y, _image.get_width(), _image.get_height())):
                        draw = False
                    if draw:
                        self.blit(_image.surface, (x, y))
                        self.surfaces.append(pygame.Rect(x, y, _image.get_width(), _image.get_height()))
                        _images.remove(_image)
                        if image_num >= len(_images):
                            return _images
                        _image = _images[image_num]
                        continue
            image_num += 1
            if image_num >= len(_images):
                break
            _image = _images[image_num]

        return _images


class Button(pygame.Surface):
    def __init__(self, x, y, title, color=(255, 0, 0)):
        pygame.Surface.__init__(self, (100, 100))
        self.title = title
        self.pos = (x, y)
        self.font = pygame.font.SysFont("sfcompactdisplay", 14, bold=True)
        self.fill(color)
        self.text = self.font.render(title, True, (0, 0, 0))
        self.blit(self.text, (self.get_width() / 2 - self.text.get_width() / 2,
                              self.get_height() / 2 - self.text.get_height() / 2,
                              self.get_width() / 2 + self.text.get_width() / 2,
                              self.get_height() / 2 + self.text.get_height() / 2))

    def draw(self, surf):
        surf.blit(self, self.pos)

    def collide(self, x, y):
        return (self.pos[0] <= x <= self.pos[0] + self.get_width() and
                self.pos[1] <= y <= self.pos[1] + self.get_height())


class Controls(pygame.Surface):
    def __init__(self, on_click_listener):
        pygame.Surface.__init__(self, (1024, 100))

        self.buttons = []
        self.on_click_listener = on_click_listener
        self.init_buttons()

    def init_buttons(self):
        self.buttons.append(Button(0, 0, "NinePatch"))
        self.buttons.append(Button(110, 0, "LEFT"))
        self.buttons.append(Button(220, 0, "RIGHT"))
        self.buttons.append(Button(330, 0, "BOTTOM"))
        self.buttons.append(Button(440, 0, "TOP"))
        self.buttons.append(Button(550, 0, "DONE", color=(0, 255, 0)))

    def touch_input(self, (x, y)):
        if x < self.get_width() and y < self.get_height():
            for button in self.buttons:
                if button.collide(x, y):
                    self.on_click_listener(button)

    def draw(self, surf):
        self.fill((255, 255, 255))
        for button in self.buttons:
            self.blit(button, button.pos)
        surf.blit(self, (0, 0))


image_filename_list = get_image_assets(get_assets(os.getcwd()), get_assets_folder(os.getcwd()))

print image_filename_list
images = get_images(image_filename_list)
images = sorted(images, key=lambda image: image.get_height())[::-1]
images = sorted(images, key=lambda image: image.get_width())[::-1]


def get_1024_a(_images):
    r_images = list()
    big_picture = BigPicture(1024, 1024)
    for _image in _images:
        if not big_picture.add_image(_image):
            r_images.append(_image)
    return big_picture, r_images


def get_1024_b(_images):
    big_picture = BigPicture(1024, 1024)
    r_images = big_picture.add_images(_images)
    return big_picture, r_images


def better_fill():
    start = current_milli_time()

    f = images
    big_pictures = list()
    x = 1
    dynamic_out.append("AssetManager mAssetManager = AssetManager.getInstance();");
    while len(f) > 0:
        dynamic_out.append("mAssetManager.addTexture(\"big_picture_a_" + str(x) + ".png\");")
        image, f = get_1024_a(f)
        pygame.image.save(image, "big_picture_a_" + str(x) + ".png")
        big_pictures.append(image)
        x += 1

    print "Total Time 1: " + str((current_milli_time() - start))

    return big_pictures


def faster_fill():
    start = current_milli_time()

    f = images
    x = 1
    while len(f) > 0:
        image, f = get_1024_b(f)
        pygame.image.save(image, "big_picture_b_" + str(x) + ".png")
        x += 1

    print "Total Time 2: " + str((current_milli_time() - start))


image_num = 0
image = images[image_num]

CONTROL_NONE = 0
CONTROL_LEFT = 1
CONTROL_RIGHT = 2
CONTROL_BOTTOM = 3
CONTROL_TOP = 4
control = CONTROL_NONE


def onclick(button):
    global image, image_num, control
    if "ninepatch" in button.title.lower():
        image.toggle_ninepatch()
        control = CONTROL_NONE
    elif "done" in button.title.lower():
        image_num += 1
        image = images[image_num]
        control = CONTROL_NONE
    elif "left" in button.title.lower():
        control = CONTROL_LEFT
    elif "right" in button.title.lower():
        control = CONTROL_RIGHT
    elif "bottom" in button.title.lower():
        control = CONTROL_BOTTOM
    elif "top" in button.title.lower():
        control = CONTROL_TOP


def go_through():
    surf = pygame.display.set_mode((1024, 1024))
    clock = pygame.time.Clock()
    controls = Controls(onclick)

    while True:
        try:
            for event in pygame.event.get():
                if event.type == QUIT:
                    pygame.quit()
                    sys.exit()
                elif event.type == MOUSEBUTTONDOWN:
                    controls.touch_input(event.pos)
                elif event.type == KEYUP:
                    print control
                    if control != CONTROL_NONE:
                        if event.key == K_LEFT:
                            if control == CONTROL_LEFT:
                                image.left(-1)
                            elif control == CONTROL_RIGHT:
                                image.right(-1)
                        elif event.key == K_RIGHT:
                            if control == CONTROL_LEFT:
                                image.left(1)
                            elif control == CONTROL_RIGHT:
                                image.right(1)
                        elif event.key == K_UP:
                            if control == CONTROL_TOP:
                                image.top(-1)
                            elif control == CONTROL_BOTTOM:
                                image.bottom(1)
                        elif event.key == K_DOWN:
                            if control == CONTROL_TOP:
                                image.top(1)
                            elif control == CONTROL_BOTTOM:
                                image.bottom(-1)

            surf.fill((128, 128, 128))
            controls.draw(surf)
            image.draw(surf)
            pygame.display.update()
            clock.tick(60)
        except:
            break


go_through()
better_fill()

print "\n STATIC \n"
for staticline in static_out:
    print staticline

print "\n\n DYNAMIC \n\n"
for dynamic_line in dynamic_out:
    print dynamic_line

# for i in range(10, 20):
#     p = pygame.Surface((random.randint(100, 500), random.randint(100, 500)))
#     color = random.randint(50, 200)
#     p.fill((color, color, color))
#     pygame.image.save(p, "_" + str(i) + ".png")

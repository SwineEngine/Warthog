import swine

# import ../../game

scene = swine.Scene(None)

game_object = swine.GameObject("")
# For some reason it has to be set like this
# even though it can be set through the constructor in the library example
# https://github.com/SwineEngine/swine/blob/swine3/examples/__main__.py#L16
game_object.name = "Object"

transform = swine.Transform((0, 0, 0), (0, 0, 0, 0), (1, 1, 1))

def empty_draw():
    return {"vertices": [], "indices": [], "shader_name": ""}

transform.draw = empty_draw

game_object.add_component(transform)

rectangle_render = swine.RectangleRender((0.15, 0.15))

def rect_draw():
    bottom_left = (-1, -1)
    top_left = (-1, 1)
    top_right = (1, 1)
    bottom_right = (1, -1)

    vertices = [bottom_left, top_left, bottom_right, top_right]
    indices = [0, 1, 2, 2, 3, 1]

    return {"vertices": vertices, "indices": indices, "shader_name": "rectangle"}

rectangle_render.draw = rect_draw

game_object.add_component(rectangle_render)

# Again, even though it works in the example,
# GameObject#get_components() will return null,
# unless it's overridden like this
def get_comps():
    return [transform, rectangle_render]
game_object.get_components = get_comps

scene.add_object(game_object)
# game.window.add_scene(scene)

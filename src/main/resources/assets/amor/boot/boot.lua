local amor = {}

function amor.main()
    -- Default configuration
    amor.config = {
        version = love._version,
        window = {
            title = "Untitled Amor Game",
            width = 800,
            height = 600,
            resizable = false,
        },
        modules = {
            audio = true,
            graphics = true,
            keyboard = true,
            system = true,
            window = true,
        },
    }

    -- Get the configuration
    pcall(require, "conf")
    pcall(love.conf, amor.config)

    -- Load the required modules
    -- Gets desired modules.
    for _,v in ipairs{
        "audio",
        "graphics",
        "keyboard",
        "system",
        "window",
    } do
        if amor.config.modules[v] then
            _requireModule(v)
        end
    end

    -- Get the game main
    pcall(require, "main")
end

return amor

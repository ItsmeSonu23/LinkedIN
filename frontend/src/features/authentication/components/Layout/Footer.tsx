import { NavLink } from "react-router"

export const Footer = () => {
    return (
        <footer className="text-md bg-white">
            <ul className="max-w-[74rem] w-full my-0 mx-auto text-black/60 flex items-center flex-wrap gap-4 p-0 mt-8">
                <li className="flex items-center gap-2">
                    <img className="w-[4rem] " src="/DarkCheckInLogo.png" alt="Dark Checked In Logo" />
                    <span> ©2025</span>
                </li>
                <li>
                    <NavLink to={""}>Accessibility</NavLink>
                </li>
                <li>
                    <NavLink to={""}>User Agreement</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Privacy Policy</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Cookie Policy</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Copyright Policy</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Brand Policy</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Guest Controls</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Community Guidelines</NavLink>
                </li>
                <li>
                    <NavLink to={""}>Language</NavLink>
                </li>
            </ul>
        </footer>
    )
}


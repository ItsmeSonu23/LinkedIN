import { NavLink } from "react-router"

export const Header = () => {
  return (
    <div className="max-w-[74rem] w-full my-0 mx-auto">
      <NavLink to={"/"}>
        <img className="w-[7rem]" src="/LightCheckedInLogo.png" alt="Light Checked In Logo" />
      </NavLink>
    </div>
  )
}

    
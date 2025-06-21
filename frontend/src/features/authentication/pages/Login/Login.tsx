import { NavLink } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { Separator } from "../../components/Separator/Separator"
import { useState } from "react"

export const Login = () => {
  const [errorMessage, setErrorMessage] = useState<string | null>("");
  return (
    <Box>
      <h1 className="text-[2rem] font-bold mb-4">Sign in</h1>
      <p>Stay updated on your professional world</p>

      <form>
        <Input type="email" id="email" label="Email" onFocus={() => setErrorMessage("")} />

        <Input type="password" id="password" label="Password" onFocus={() => setErrorMessage("")} />
        {
          errorMessage &&
          <div className="text-red-500 text-sm mb-4">
            {errorMessage}
          </div>
        }
        <Button type="submit" >Sign in</Button>
        <NavLink 
        to={"/request-password-reset"} className={"text-center font-bold text-violet-600"}>Forget Password?</NavLink>
      </form>
      <Separator>Or</Separator>
      <div className="text-center w-full">
        <span>New to CheckedIn?</span> <NavLink className="text-violet-600 font-bold " to={"/signup"}>Join Now</NavLink>
      </div>
    </Box>
  )
}


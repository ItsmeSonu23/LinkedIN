import { NavLink } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { Separator } from "../../components/Separator/Separator"
import { useState } from "react"

export const Signup = () => {
  const[errorMessage, setErrorMessage] = useState<string | null>("jhxcb");
  return (
    <Box>
        <h1 className="text-[2rem] font-bold mb-4">Sign up</h1>
        <p>Make the most of your professional life.</p>
        <form>
            <Input type="email" id="email" label="Email" />
            <Input type="password" id="password" label="Password" />

            {
              errorMessage && 
              <div className="text-red-500 text-sm mb-4">
                {errorMessage}
              </div>
            }

            <p className="text-xs">By clicking Agree & Join or Continue, you agree to CheckedIn's <a className="text-violet-600 font-bold" href="">User Agreement</a>, <a className="text-violet-600 font-bold" href="">Privacy Policy</a>, and <a className="text-violet-600 font-bold" href="">Cookie Policy</a>.</p>
            <Button type="submit" >Agree & Join</Button>
        </form>
        <Separator>Or</Separator>
        <div className="text-center w-full">
            Already on CheckedIn? <NavLink className="text-violet-600 font-bold " to={"/login"}>Sign in</NavLink>
        </div>
    </Box>
  )
}

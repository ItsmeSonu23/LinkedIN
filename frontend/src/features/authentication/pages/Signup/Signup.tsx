import { NavLink, useLocation, useNavigate } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { Separator } from "../../components/Separator/Separator"
import { useState } from "react"
import { useAuthentication } from "../../contexts/AuthenticationContextProvider"

export const Signup = () => {
  const[errorMessage, setErrorMessage] = useState<string | null>("");
  const[isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const {signup} = useAuthentication();
  const doSignup = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const email = (e.currentTarget.email as HTMLInputElement).value;
    const password = (e.currentTarget.password as HTMLInputElement).value;
    try{
      await signup(email, password);
      navigate("/");
    }catch (error) {
      if(error instanceof Error) {
        setErrorMessage(error.message);
      }else{
        setErrorMessage("An unexpected error occurred.");
      }
    }finally {
      setIsLoading(false);
    }
  }
  return (
    <Box>
        <h1 className="text-[2rem] font-bold mb-4">Sign up</h1>
        <p>Make the most of your professional life.</p>
        <form onSubmit={doSignup} className="flex flex-col gap-2 mt-8">
            <Input type="email" id="email" label="Email" />
            <Input type="password" id="password" label="Password" />

            {
              errorMessage && 
              <div className="text-red-500 text-sm mb-4">
                {errorMessage}
              </div>
            }

            <p className="text-xs">By clicking Agree & Join or Continue, you agree to CheckedIn's <a className="text-violet-600 font-bold" href="">User Agreement</a>, <a className="text-violet-600 font-bold" href="">Privacy Policy</a>, and <a className="text-violet-600 font-bold" href="">Cookie Policy</a>.</p>
            <Button disabled={isLoading} type="submit" >Agree & Join</Button>
        </form>
        <Separator>Or</Separator>
        <div className="text-center w-full">
            Already on CheckedIn? <NavLink className="text-violet-600 font-bold " to={"/login"}>Sign in</NavLink>
        </div>
    </Box>
  )
}

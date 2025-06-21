import { NavLink, useLocation, useNavigate } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { Separator } from "../../components/Separator/Separator"
import { useState, type FormEvent } from "react"
import { useAuthentication } from "../../contexts/AuthenticationContextProvider"

export const Login = () => {
  const [errorMessage, setErrorMessage] = useState<string | null>("");
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuthentication();
  const[isLoading, setIsLoading] = useState(false);

  const doLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const email = (e.currentTarget.email as HTMLInputElement).value;
    const password = (e.currentTarget.password as HTMLInputElement).value;

    try {
      await login(email, password);
      const dest = location.state?.from || "/";
      navigate(dest);
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unexpected error occurred");
      }
    }finally{
      setIsLoading(false);
    }
  }

  return (
    <Box>
      <h1 className="text-[2rem] font-bold mb-4">Sign in</h1>
      <p>Stay updated on your professional world</p>

      <form onSubmit={doLogin} className="flex flex-col gap-2 mt-8">
        <Input type="email" id="email" label="Email" onFocus={() => setErrorMessage("")} />

        <Input type="password" id="password" label="Password" onFocus={() => setErrorMessage("")} />
        {
          errorMessage &&
          <div className="text-red-500 text-sm mb-4">
            {errorMessage}
          </div>
        }
        <Button type="submit" disabled={isLoading}>{
          isLoading ? "..." : "Sign in"}
        </Button>
        <NavLink 
        to={"/request-password-reset"} className={"font-bold text-violet-600"}>Forget Password?</NavLink>
      </form>
      <Separator>Or</Separator>
      <div className="text-center w-full">
        <span>New to CheckedIn?</span> <NavLink className="text-violet-600 font-bold " to={"/signup"}>Join Now</NavLink>
      </div>
    </Box>
  )
}


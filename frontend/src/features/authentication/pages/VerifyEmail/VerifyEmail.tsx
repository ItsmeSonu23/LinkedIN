import { useState } from "react"
import { Box } from "../../components/Box/Box"
import { Input } from "../../components/Input/Input"
import { Button } from "../../components/Button/Button"
import { useNavigate } from "react-router-dom"

export const VerifyEmail = () => {
  const [errorMessage, setErrorMessage] = useState("")
  const[message, setMessage] = useState("")
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const validateEmail = async(code:string)=>{
    setMessage("");
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/authentication/validate-email-verification-token?token=${code}`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      if (response.ok) {
        setErrorMessage("");
        navigate("/")
      }
        const { message } = await response.json();
        setErrorMessage(message || "An error occurred while validating the email.");
    } catch (error) {
      console.log(error);
      setErrorMessage("An error occurred while validating the email.");
    }finally{
      setIsLoading(false);
    }
  }

  const sendEmailVerificationToken = async()=>{
    setMessage("");
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/authentication/validate-email-verification-token`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      if (response.ok) {
        setErrorMessage("");
        setMessage("Verification email sent successfully. Please check your inbox.");
        return;
      }
        const { message } = await response.json();
        setErrorMessage(message || "An error occurred while validating the email.");
    } catch (error) {
      console.log(error);
      setErrorMessage("An error occurred while validating the email.");
    }finally{
      setIsLoading(false);
    }
  }


  return (
    <Box>
        <h1 className="text-[2rem] font-bold mb-4">Verify your email</h1>
        <form onSubmit={async(e) => {
          e.preventDefault();
          setIsLoading(true);
          const code = (e.currentTarget.email as HTMLInputElement).value;
          await validateEmail(code);
          setIsLoading(false);
        }}>
          <p>Only one step left to complete your registration. Verify your email address.</p>

          <Input name="email" type="text" label="Verification Code" />
          {
            message && <p className="text-green-500 text-sm mb-4">{message}</p>
          }
          {
            errorMessage && <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          }
          <Button type="submit" className="mt-4" disabled={isLoading}>
            Validate email
          </Button>
          <Button type="button" outline
            className="mt-4"
            disabled={isLoading} onClick={() => {
              sendEmailVerificationToken()
            }}>
            Send again
          </Button>
        </form>
    </Box>
  )
}

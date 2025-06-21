import { useNavigate } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { useState } from "react"

export const ResetPassword = () => {
  const navigate = useNavigate()
  const [emailSent, setEmailSent] = useState(false)
  const [errorMessage, setErrorMessage] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const[email, setEmail] = useState<string>("");

  const sendPasswordResetToken = async (email: string) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/authentication/send-password-reset-token?email=${email}`, {
        method: "PUT",
      });
      if (response.ok) {
        setEmailSent(true);
        setErrorMessage("");
        return;
      }
      const { message } = await response.json();
      setErrorMessage(message);

    } catch (error) {
      console.error(error);
      setErrorMessage("An unexpected error occurred. Please try again.");
    } finally {
      setIsLoading(false);
    }
  }

  const resetPassword = async (email: string, code: string, newPassword: string) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/authentication/reset-password?email=${email}&token=${code}&newPassword=${newPassword}`, {
        method: "PUT",
      });
      if (response.ok) {
        setErrorMessage("");
        navigate("/login");
      }
      const { message } = await response.json();
      setErrorMessage(message);
    } catch (error) {
      console.error(error);
      setErrorMessage("An unexpected error occurred. Please try again.");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <Box>
      <h1 className="text-[2rem] font-bold mb-4">Reset Password</h1>
      {
        !emailSent ? <form
          onSubmit={async (e) => {
            e.preventDefault();
            setIsLoading(true);
            const email = (e.currentTarget.email as HTMLInputElement).value;
            await sendPasswordResetToken(email);
            setEmail(email);
            setIsLoading(false);
          }}>
          <p>Enter your email and we'll send a verification code if it matches an existing CheckedIn account.</p>
          <Input name="email" type="email" label="Email" />
          <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          <Button type="submit" className="mt-4" onClick={() => setEmailSent(true)}>
            Send Verification Code
          </Button>
          <Button type="button" outline
            className="mt-4"
            onClick={() => navigate("/login")}>
            Back
          </Button>
        </form> : <form
          onSubmit={async (e) => {
            e.preventDefault();
            setIsLoading(true);
            const code = (e.currentTarget.code as HTMLInputElement).value;

            const newPassword = (e.currentTarget.newPassword as HTMLInputElement).value;
            await resetPassword(email, code, newPassword);
            setIsLoading(false);
          }
        >
          <p>Enter the verification code we sent to your email and your new password.</p>
          <Input type="text" label="Verification Code" />
          <Input type="password" label="New Password" />

          <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          <Button type="submit" className="mt-4">
            Reset Password
          </Button>
          <Button type="button" outline
            className="mt-4"
            onClick={() =>{
              setErrorMessage("");
              setEmailSent(false);
            }}>
            Back
          </Button>
        </form>
      }
    </Box>
  )
}


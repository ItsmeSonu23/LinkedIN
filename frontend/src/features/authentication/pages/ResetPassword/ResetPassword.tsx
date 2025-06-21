import { useNavigate } from "react-router"
import { Box } from "../../components/Box/Box"
import { Button } from "../../components/Button/Button"
import { Input } from "../../components/Input/Input"
import { useState } from "react"

export const ResetPassword = () => {
  const navigate = useNavigate()
  const [emailSent, setEmailSent] = useState(false)
  const [errorMessage, setErrorMessage] = useState("")
  return (
    <Box>
      <h1 className="text-[2rem] font-bold mb-4">Reset Password</h1>
      {
        !emailSent ? <form>
          <p>Enter your email and we'll send a verification code if it matches an existing CheckedIn account.</p>
          <Input name="email" type="email" label="Email" />
          <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          <Button type="submit" className="mt-4" onClick={()=>setEmailSent(true)}>
            Send Verification Code
          </Button>
          <Button type="button" outline
            className="mt-4"
            onClick={() => navigate("/login")}>
            Back
          </Button>
        </form> : <form>
          <p>Enter the verification code we sent to your email and your new password.</p>
          <Input  type="text" label="Verification Code" />
          <Input type="password" label="New Password" />

          <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          <Button type="submit" className="mt-4">
            Reset Password
          </Button>
          <Button type="button" outline
            className="mt-4"
            onClick={() => setEmailSent(false)}>
            Back
          </Button>
        </form>
      }
    </Box>
  )
}


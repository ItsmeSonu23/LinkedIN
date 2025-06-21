import { useState } from "react"
import { Box } from "../../components/Box/Box"
import { Input } from "../../components/Input/Input"
import { Button } from "../../components/Button/Button"

export const VerifyEmail = () => {
  const [errorMessage, setErrorMessage] = useState("")
  const[message, setMessage] = useState("")
  return (
    <Box>
        <h1 className="text-[2rem] font-bold mb-4">Verify your email</h1>
        <form>
          <p>Only one step left to complete your registration. Verify your email address.</p>

          <Input name="email" type="text" label="Verification Code" />
          {
            message && <p className="text-green-500 text-sm mb-4">{message}</p>
          }
          {
            errorMessage && <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
          }
          <Button type="submit" className="mt-4">
            Validate email
          </Button>
          <Button type="button" outline
            className="mt-4"
            onClick={() => setMessage("A verification email has been sent to your email address.")}>
            Send again
          </Button>
        </form>
    </Box>
  )
}

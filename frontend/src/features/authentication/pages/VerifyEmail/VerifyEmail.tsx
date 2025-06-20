import { Box } from "../../components/Box/Box"

export const VerifyEmail = () => {
  return (
    <Box>
        <h1>Verify Email</h1>
        <p>Please check your email for a verification link.</p>
        <p>If you didn't receive an email, you can request a new one.</p>
        <button>Resend Verification Email</button>
    </Box>
  )
}

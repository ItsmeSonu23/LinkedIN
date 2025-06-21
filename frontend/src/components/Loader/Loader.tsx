const Loader = () => {
  return (
    <div className="flex flex-col items-center h-screen w-full">
      <img className="w-[10rem] h-auto mt-[12rem]" src="/LightCheckedInLogo.png" alt="Loader Logo" />
      <div className="h-[0.1rem] w-[10rem] rounded-[9999px] bg-gray-200 ">
        <div className="h-[0.1rem] rounded-[9999px] w-16 bg-gray-400 animate-slider "></div>
      </div>
    </div>
  )
}

export default Loader

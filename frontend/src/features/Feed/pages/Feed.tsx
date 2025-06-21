export const Feed = () => {
  return (
    <div className="min-h-screen grid grid-rows-[auto_1fr] gap-4 ">
      <header className="bg-[#eaeaea] h-12 rounded flex justify-end items-center gap-4 p-2">
        <div className="">Hello user@example.com</div>
        <span>|</span>
        <button>Logout</button>
      </header>
      <main className="grid grid-rows-[10rem_1fr_10rem] gap-8 ps-4 pe-4 max-w-[74rem] my-0 mx-auto w-full min-xl:grid-cols-[15rem_1fr_18rem] min-xl:grid-rows-auto">
        <div className="bg-[#eaeaea] min-xl:h-[30rem]"></div>
        <div className="grid h-full grid-rows-[7rem_1fr] gap-2">
          <div className="bg-[#eaeaea]"></div>
          <div className="bg-[#eaeaea]"></div>
        </div>
        <div className="bg-[#eaeaea] min-xl:h-[20rem]"></div>
      </main>
    </div>
  )
}

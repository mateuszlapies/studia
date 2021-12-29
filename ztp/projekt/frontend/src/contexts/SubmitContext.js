import React from "react";
export const SubmitContext = React.createContext({list: [], changeList: (b,d) => {return b+d;}})
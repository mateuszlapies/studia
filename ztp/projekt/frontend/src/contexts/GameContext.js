import React from "react";
export const GameContext = React.createContext({game: {players: [], cezar: undefined, card: undefined}, updateGame: (d) => {}});
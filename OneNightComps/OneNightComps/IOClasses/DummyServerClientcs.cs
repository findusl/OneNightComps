﻿using Newtonsoft.Json;
using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    class DummyServerClient : IServerClient
    {

        public async Task<resultType> GetObjects<resultType>(string path, Dictionary<string, string> query = null)
        {
            //simulate network delay
            await Task.Delay(500);
            if (path.Equals("one_night_comps/game_role/read.php"))
                return JsonConvert.DeserializeObject<resultType>("[{\"id\":\"1\",\"name\":\"Villager\",\"description\":\"The Villager has no special abilities, but he is definitly not a werewolf. Players may often claim to be a villager.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"2\",\"name\":\"Werewolf\",\"description\":\"At night, all Werewolves open their eyes and look for other werewolves. If no one else opens their eyes, the other Werewolves are in the center. \",\"faction\":{\"id\":\"2\",\"name\":\"Werewolf\"}},{\"id\":\"3\",\"name\":\"Minion\",\"description\":\"Immediately following the Werewolf phase at night, the Minion wakes up and sees who the Werewolves are. During this phase, all Werewolves put their thumbs up\\r\\nso the Minion can see who they are. The Werewolves don\\u00e2\\u0080\\u0099t know who the Minion is. If the Minion dies and no Werewolves die, the Werewolves (and the Minion) win. If no players are Werewolves, the Minion wins as long as one other player (not the Minion) dies. This role can be a very powerful ally for the werewolf team. \",\"faction\":{\"id\":\"2\",\"name\":\"Werewolf\"}},{\"id\":\"4\",\"name\":\"Mason\\r\\n\",\"description\":\"When using the Masons always put both Masons in the game. The Mason wakes up at night and looks for the other Mason. If the Mason doesn\\u00e2\\u0080\\u0099t see another Mason, it means the other Mason card is in the center.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"5\",\"name\":\"Seer\",\"description\":\"At night, the Seer may look either at one other player\\u00e2\\u0080\\u0099s card or at two of the center cards, but does not move them.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"6\",\"name\":\"Robber\",\"description\":\"At night, the Robber may choose to rob a card from another player and place his Robber card where\\r\\nthe other card was. Then the Robber looks at his new card. The player who receives the Robber card is on the village team. The Robber is on the team of the card he takes, however, he does not do the action of his new role at night.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"7\",\"name\":\"Troublemaker\",\"description\":\"At night, the Troublemaker may switch the cards of two other players without looking at those cards. The players who receive a different card are now the role (and team) of their new card, even though they don\\u00e2\\u0080\\u0099t know what role that is until the end of the game. The Troublemaker is on the village team.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"8\",\"name\":\"Drunk\",\"description\":\"The Drunk is so drunk that he doesn\\u00e2\\u0080\\u0099t remember his role. When it comes time to wake up at night, he must exchange his Drunk card for any card in the center, but he does not look at it. The Drunk is now the new role in front of him (even though he doesn\\u00e2\\u0080\\u0099t know what that new role is) and is on that team.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"9\",\"name\":\"Insomniac\",\"description\":\"The Insomniac wakes up and looks at her card (to see if it has changed). Only use the Insomniac if the Robber and\\/or the Troublemaker are in the game.\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}},{\"id\":\"10\",\"name\":\"Tanner\",\"description\":\"The Tanner hates his job so much that he wants to die. The Tanner only wins if he dies. If the Tanner dies and no Werewolves die, the Werewolves do not win. If the Tanner dies and a Werewolf also dies, the village team wins too. The Tanner is considered a member of the village (but is not on their team), so if the Tanner dies when all werewolves are in the center, the village team loses.\",\"faction\":{\"id\":\"3\",\"name\":\"Tanner\"}},{\"id\":\"11\",\"name\":\"Hunter\",\"description\":\"If the Hunter dies, the player he is pointing at dies as well (regardless of how many votes his target receives).\",\"faction\":{\"id\":\"1\",\"name\":\"Villagers\"}}]");
            else if (path.Equals("one_night_comps/game_composition/read.php"))
                return JsonConvert.DeserializeObject<resultType>("[]");
            throw new NotImplementedException();
        }

        public async Task<resultType> PostObjects<resultType>(string path, string parameters, Dictionary<string, string> query = null)
        {
            //simulate network delay
            await Task.Delay(500);
            if (path.Equals("one_night_comps/user/register.php"))
                return (resultType)(object)true;
            else if (path.Equals("one_night_comps/user/login.php"))
                return (resultType)(object)"token";
            throw new NotImplementedException();
        }
    }
}

import React from 'react';
import { createAppContainer, createStackNavigator } from 'react-navigation';

import Index from './IndexScreen';
import Details from './DetailsScreen';

const App = createStackNavigator({
  Index: {
    screen: Index,
    navigationOptions: {
      headerTitle: 'Index',
    },
  },
  Details: {
    screen: Details,
    navigationOptions: ({ navigation }) => ({
      headerTitle: 'Details',
    }),
  },
});

export default createAppContainer(App);

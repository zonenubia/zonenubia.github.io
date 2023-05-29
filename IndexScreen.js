import React from 'react';
import { View, Button } from 'react-native';

export default ({ navigation }) => (
  <View>
    <Button
      title="Go To Details Screen"
      onPress={() => navigation.navigate('Details')}
    />
  </View>
);

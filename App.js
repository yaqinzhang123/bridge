/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  NativeModules
} from 'react-native';

const icToastAndroid = NativeModules.ICToastAndroid;

 class App extends Component {
  state = { obj: '' }


  readIDCard(){
     icToastAndroid.read((str)=>{
      console.log(str);
      this.setState({obj:str});
     });
  }
  printIDCard(){
    console.log(this.state.obj)
     icToastAndroid.print(
      '**当场行政处罚决定书<br/>'+
      '案号：北运管决定书[2017]0000001<br/>'+
      '【当事人】张三<br/>'+
      '【身份证号】130322199207088912<br/>'+
      '【住址】河北省秦皇岛市海港区秦皇西大街112号<br/>'+
      '**你（单位）不按规定车道行驶驾驶的行为违反了《中华人民共和国道路交通安全法实施条例》第四十四条的规定，依据《中华人民共和国道路交通安全法》第九十条的规定，决定给予罚款150元，同时记3分的行政处罚。<br/>'+
      '**如果不服本处罚决定，可以依法在六十日内向北戴河区交通运输局或北戴河区人民政府申请行政复议，或者在六个月内向北戴河区人民法院提起行政诉讼，但本决定不停止执行，法律另有规定的除外。逾期不申请行政复议、不提起行政诉讼又不履行的，本机关将依法申请人民法院强制执行。<br/>'+
      '执法人员证件号：0037829<br/>'+
      '时间：2017-12-28<br/>','1');
      //'data:image/png;base64,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'+
//'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');
  }

  render() {
    return (
      <View>
        <TouchableOpacity 
        onPress={this.readIDCard.bind(this)}
        style={styles.containerStyle}>
        <Text>Read</Text>
        </TouchableOpacity>
        <TouchableOpacity 
        onPress={this.printIDCard.bind(this)}
        style={styles.containerStyle}>
        <Text>Print</Text>
        </TouchableOpacity>
      <Text>{this.state.obj}</Text>
      </View>
    );
  }
}

const styles = {
 containerStyle: {
   height: 50,
   borderWidth: 1,
   borderColor: '#000',
   borderRadius: 5,
   padding: 3,
   margin: 5,
   justifyContent: 'center',
   alignItems: 'center'
 }
};
export default App;
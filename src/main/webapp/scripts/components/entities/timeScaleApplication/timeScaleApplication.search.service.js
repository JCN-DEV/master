'use strict';

angular.module('stepApp')
    .factory('TimeScaleApplicationSearch', function ($resource) {
        return $resource('api/_search/timeScaleApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('TimeScaleApplicationCheck', function ($resource) {
        return $resource('api/timescaleApplications/instEmployee/:code', {}, {
            'get':{
                method: 'GET',
                transformResponse: function (data) {
                    if(!data){
                        data={id:0};
                    }
                    data = angular.fromJson(data);

                    return data;
                }
            }
            /*'get': { method: 'GET', isArray: false}*/
        });
    }).factory('TimescaleApplicationList', function ($resource) {
        return $resource('api/timescaleApplications/timescaleList/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('TimescaleApApplicationList', function ($resource) {
              return $resource('api/timescaleApplications/timescaleApList/:status', {}, {
                  'query': {method: 'GET', isArray: true}
              });
    }).factory('TimescaleSummaryList', function ($resource) {
        return $resource('api/timescaleApplications/SummaryList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('TimescaleApplicationsByLevel', function ($resource) {
        return $resource('api/timescaleApplications/timescaleList/:status/:levelId', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('TimescaleApplicationDecline', function ($resource) {
        return $resource('api/timeScaleApplications/decline/:id', {}, {
            'decline': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });

    }).factory('TimeScaleApplicationCount', function ($resource) {
        return $resource('api/timeScaleApplications/mpoList/:status', {}, {
            'get':{
                method: 'GET',
                transformResponse: function (data)
                {
                    if(data){
                        data = angular.fromJson(data);
                        data = {size:data.length};
                        data = angular.fromJson(data);
                    }
                    //console.log(data);
                    return data;
                }
            }
            /*'get': { method: 'GET', isArray: false}*/
        });
    }).factory('TimeScaleApplicationLis', function ($resource) {
        return $resource('api/timeScaleApplications/mpoList/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })/*.factory('TimeScaleSummaryList', function ($resource) {
        return $resource('api/timescaleApplications/SummaryList', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })*/;


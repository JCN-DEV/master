'use strict';

angular.module('stepApp')
    .factory('APScaleApplicationSearch', function ($resource) {
        return $resource('api/_search/aPScaleApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('APScaleApplicationCheck', function ($resource) {
        return $resource('api/aPscaleApplications/instEmployee/:code', {}, {
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
    }).factory('APcaleApplicationList', function ($resource) {
        return $resource('api/aPScaleApplications/aPScaleList/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('APscaleSummaryList', function ($resource) {
        return $resource('api/aPScaleApplications/summaryList', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('APApplicationsByLevel', function ($resource) {
        return $resource('api/aPScaleApplications/aPList/:status/:levelId', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('APApplicationDecline', function ($resource) {
        return $resource('api/aPScaleApplications/decline/:id', {}, {
            'decline': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });

    });

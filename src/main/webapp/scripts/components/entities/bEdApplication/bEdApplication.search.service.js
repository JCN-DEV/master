'use strict';

angular.module('stepApp')
    .factory('BEdApplicationSearch', function ($resource) {
        return $resource('api/_search/bEdApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('BEdApplicationList', function ($resource) {
        return $resource('api/bedApplication/bedList/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('BEdSummaryList', function ($resource) {
        return $resource('api/bEdApplications/summaryList', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('BEdApplicationsByLevel', function ($resource) {
        return $resource('api/bedApplication/bedList/:status/:levelId', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('BEdApplicationDecline', function ($resource) {
        return $resource('api/bEdApplications/decline/:id', {}, {
            'decline': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });

    }).factory('BEdApplicationCheck', function ($resource) {
        return $resource('api/bEdApplications/instEmployee/:code', {}, {
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
    });

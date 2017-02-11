'use strict';

angular.module('stepApp')
    .factory('AssetRepair', function ($resource, DateUtils) {
        return $resource('api/assetRepairs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfProblem = DateUtils.convertLocaleDateFromServer(data.dateOfProblem);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateOfProblem = DateUtils.convertLocaleDateToServer(data.dateOfProblem);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateOfProblem = DateUtils.convertLocaleDateToServer(data.dateOfProblem);
                    return angular.toJson(data);
                }
            }
        });
    });
    /*.factory('assetCodeAssetRepair', function ($resource) {
        return $resource('api/assetRepairs/findAssetRecordAssetCode/:assetCode', {}, {
            'get': { method: 'GET', isArray: false}
        });
    })*/


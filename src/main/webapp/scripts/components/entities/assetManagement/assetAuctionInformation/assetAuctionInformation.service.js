'use strict';

angular.module('stepApp')
    .factory('AssetAuctionInformation', function ($resource, DateUtils) {
        return $resource('api/assetAuctionInformations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastRepairDate = DateUtils.convertLocaleDateFromServer(data.lastRepairDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastRepairDate = DateUtils.convertLocaleDateToServer(data.lastRepairDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastRepairDate = DateUtils.convertLocaleDateToServer(data.lastRepairDate);
                    return angular.toJson(data);
                }
            }
        });
    });

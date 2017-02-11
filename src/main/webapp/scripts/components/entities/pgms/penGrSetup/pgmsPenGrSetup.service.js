'use strict';

angular.module('stepApp')
    .factory('PgmsPenGrSetup', function ($resource, DateUtils) {
        return $resource('api/pgmsPenGrSetups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.effectiveDate = DateUtils.convertLocaleDateFromServer(data.effectiveDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PgmsPenGrRateList', function ($resource) {
              return $resource('api/pgmsPenGrRateLists/:penGrSetId', {},
                  {
                      'get': { method: 'GET', isArray: true}
                  });
    });

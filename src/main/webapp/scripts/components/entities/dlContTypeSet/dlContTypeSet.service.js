'use strict';

angular.module('stepApp')
    .factory('DlContTypeSet', function ($resource, DateUtils) {
        return $resource('api/dlContTypeSets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('DlContTypeSetByName', function ($resource) {
                  return $resource('api/dlContTypeSets/dlContTypeSetNameUnique/:name', {}, {
                      'query': {method: 'GET', isArray: true}
                  });
              })
    .factory('DlContTypeSetByCode', function ($resource) {
                  return $resource('api/dlContTypeSets/dlContTypeSetCodeUnique/:code', {}, {
                      'query': {method: 'GET', isArray: true}
                  });
              })
    .factory('FindActiveTypes', function ($resource) {
        return $resource('api/dlContTypeSets/allActiveTypes', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });


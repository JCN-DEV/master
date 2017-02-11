'use strict';

angular.module('stepApp')
    .factory('PgmsAppFamilyAttach', function ($resource, DateUtils) {
        return $resource('api/pgmsAppFamilyAttachs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }).factory('PgmsAppFamilyAttachByTypeAndPension', function ($resource) {
              return $resource('api/pgmsAppFamilyAttachsByTypeAndPension/:attacheType/:familyPensionId', {},
              {
                  'get': { method: 'GET', isArray: true}
              });
          });

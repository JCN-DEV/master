/*
'use strict';

angular.module('stepApp')
    .factory('CmsCurriculum', function ($resource, DateUtils) {
        return $resource('api/cmsCurriculums/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT'}
        });
    });
*/

'use strict';

angular.module('stepApp')
    .factory('CmsCurriculum', function ($resource, DateUtils) {
        return $resource('api/cmsCurriculums/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT'}
        });
    })
    .factory('CmsCurriculumQuery', function ($resource, DateUtils) {
              return $resource('api/cmsCurriculums/findAllcmsCurriculumByOrderID/', {}, {
                  'query': { method: 'GET', isArray: true}
              });
          }).factory('FindActivecmsCurriculums', function ($resource) {
                    return $resource('api/cmsCurriculums/allActivecmsCurriculums', {}, {
                        'query': {method: 'GET', isArray: true}
                    });
                });

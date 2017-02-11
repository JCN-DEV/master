/*
'use strict';

angular.module('stepApp')
    .factory('CmsSubAssignSearch', function ($resource) {
        return $resource('api/_search/cmsSubAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CmsSubAssignByTrade', function ($resource) {
        return $resource('/api/cmsSubAssigns/cmsTrade/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
*/


'use strict';

angular.module('stepApp')
    .factory('CmsSubAssignSearch', function ($resource) {
        return $resource('api/_search/cmsSubAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CmsSubAssignByTrade', function ($resource) {
                  return $resource('/api/cmsSubAssigns/cmsTrade/:id', {}, {
                      'query': { method: 'GET', isArray: true}
                  });
              });

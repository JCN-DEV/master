/*
'use strict';

angular.module('stepApp')
    .factory('CmsSubjectSearch', function ($resource) {
        return $resource('api/_search/cmsSubjects/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsSubByNameAndSyllabusAndCode', function ($resource) {
            return $resource('api/cmsSubjects/getCmsSubByNameAndSyllabusAndCode/:cmsSyllabusId/:code/:name', {}, {
                'query': {method: 'GET', isArray: true}
            });
        }).factory('CmsSubByCurriculum', function ($resource) {
            return $resource('api/cmsSubjects/cmsCurriculum/:id', {}, {
                'query': {method: 'GET', isArray: true}
            });
        });



*/


'use strict';

angular.module('stepApp')
    .factory('CmsSubjectSearch', function ($resource) {
        return $resource('api/_search/cmsSubjects/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsSubByNameAndSyllabusAndCode', function ($resource) {
            return $resource('api/cmsSubjects/getCmsSubByNameAndSyllabusAndCode/:cmsSyllabusId/:code/:name', {}, {
                'query': {method: 'GET', isArray: true}
            });
        }).factory('CmsSubByCurriculum', function ($resource) {
            return $resource('api/cmsSubjects/cmsCurriculum/:id', {}, {
                'query': {method: 'GET', isArray: true}
            });
        })
        .factory('FindActivecmssubjects', function ($resource) {
                      return $resource('api/cmsSubjects/allActivecmsSubjects', {}, {
                          'query': {method: 'GET', isArray: true}
                      });
                  });

'use strict';

angular.module('stepApp')
    .factory('CmsCurriculumSearch', function ($resource) {
        return $resource('api/_search/cmsCurriculums/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsCurriculumByCode', function ($resource) {
        return $resource('api/cmsCurriculums/code/:code', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })

    .factory('CmsCurriculumByName', function ($resource) {
            return $resource('api/cmsCurriculums/name/:name', {}, {
                'query': {method: 'GET', isArray: true}
            });
        });

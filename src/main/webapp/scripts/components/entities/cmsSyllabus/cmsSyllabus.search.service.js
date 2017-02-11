'use strict';

angular.module('stepApp')
    .factory('CmsSyllabusSearch', function ($resource) {
        return $resource('api/_search/cmsSyllabuss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsSyllabusByNameAndVersion', function ($resource) {
            return $resource('api/cmsSyllabuss/getCmsSyllabusByNameAndVersion/:cmsCurriculumId/:name/:version', {}, {
                'query': {method: 'GET', isArray: true}
            });
        }).factory('syllabusList', function ($resource) {
            return $resource('api/cmsSyllabuss/findCustomColumnOfCmsSyllabuss', {}, {
                'query': {method: 'GET', isArray: true}
            });
        })

        .factory('CmsSyllabusByCurriculum', function ($resource) {
                      return $resource('api/cmsSyllabuss/cmsCurriculum/:id', {}, {
                          'query': {method: 'GET', isArray: true}
                      });
                  })

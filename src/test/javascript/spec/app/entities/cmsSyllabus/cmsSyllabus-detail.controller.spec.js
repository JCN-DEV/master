'use strict';

describe('CmsSyllabus Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsSyllabus, MockCmsCurriculum;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsSyllabus = jasmine.createSpy('MockCmsSyllabus');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsSyllabus': MockCmsSyllabus,
            'CmsCurriculum': MockCmsCurriculum
        };
        createController = function() {
            $injector.get('$controller')("CmsSyllabusDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsSyllabusUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});

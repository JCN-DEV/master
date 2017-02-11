'use strict';

describe('CmsCurriculum Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsCurriculum;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsCurriculum': MockCmsCurriculum
        };
        createController = function() {
            $injector.get('$controller')("CmsCurriculumDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsCurriculumUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});

'use strict';

describe('JpSkillLevel Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpSkillLevel;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpSkillLevel = jasmine.createSpy('MockJpSkillLevel');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpSkillLevel': MockJpSkillLevel
        };
        createController = function() {
            $injector.get('$controller')("JpSkillLevelDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpSkillLevelUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
